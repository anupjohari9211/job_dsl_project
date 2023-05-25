listView('CC Flow') {
  description('This is a new CC view')
  filterBuildQueue()
  filterExecutors()

  jobs {
    job('A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD') {
      description('Load the Course details from CES database for the given production portal')
      
      // Discard Old Builds
      configure { project ->
        project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            numToKeepStr('4')
            artifactNumToKeepStr('4')
          }
        }
      }

      // Source Code Management
      scm {
        none()
      }

      // Build Triggers
      triggers {
        cron('H 02 * * 1-5')
      }

      // Configuration Matrix
      configure { project ->
        project / 'axes' << 'hudson.matrix.UserDefinedAxis' {
          name('environment')
          values('PRODUCTION')
        }
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('clientName')
          values('CS_AUS_CLIENTS')
        }
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('ingestionBucket')
          values('CS_INGESTION_BUCKET')
        }
      }

      // Build Environment
      wrappers {
        maskPasswords()
      }

      // Build Steps
      steps {
        shell('java -server -classpath ".:/opt/mlp/content-studio/lib/machine-learning-csod-cluster-computation-jar-0.0.1-SNAPSHOT-allinone.jar:/opt/mlp/library-loader/resources/" com.csod.pathshala.library.ces.CesLibraryCsvExtractorMain -c ${clientName} -e ces_prod_latest -k ${key_store_passwd} -n ${environment} -f ${ingestionBucket} -g true')
      }

      // Post-Build Actions
      publishers {
        downstream('A11. CONTENT_STUDIO_SUBSCRIPTION_EXTRACTOR_MAIN')
      }
    }

    job('A11. CONTENT_STUDIO_SUBSCRIPTION_EXTRACTOR_MAIN') {
      description('Load the Course details from CES database for the given production portal')
      
      // Discard Old Builds
      configure { project ->
        project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            numToKeepStr('4')
            artifactNumToKeepStr('4')
          }
        }
      }

      // Source Code Management
      scm {
        none()
      }
      
      // Configuration Matrix
      configure { project ->
        project / 'axes' << 'hudson.matrix.UserDefinedAxis' {
          name('environment')
          values('PRODUCTION')
        }
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('clientName')
          values('CS_AUS_CLIENTS')
        }
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('ingestionBucket')
          values('CS_INGESTION_BUCKET')
        }
      }

      // Build Environment
      wrappers {
        maskPasswords()
      }

      // Build Steps
      steps {
        shell('java -server -classpath ".:/opt/mlp/content-studio/lib/machine-learning-csod-cluster-computation-jar-0.0.1-SNAPSHOT-allinone.jar:/opt/mlp/library-loader/resources/" com.csod.pathshala.library.ces.ContentStudioSubscriptionExtractorMain -c ${clientName} -e ces_prod_latest -k ${key_store_passwd} -n ${environment} -f ${ingestionBucket} -g true')
      }

      // Post-Build Actions
      publishers {
        downstream('A2. CES_CORP_LO_CSV_LIBRARY_LOADER_MAIN')
      }
    }

    job('A2. CES_CORP_LO_CSV_LIBRARY_LOADER_MAIN') {
      description('Loads the incremental/modified data from CSV generated in GCS from previous steps into Mysql Database in LibraryContentAudited table')
      
      // Discard Old Builds
      configure { project ->
        project / 'properties' << 'jenkins.model.BuildDiscarderProperty' {
          strategy {
            numToKeepStr('4')
            artifactNumToKeepStr('4')
          }
        }
      }

      // Source Code Management
      scm {
        none()
      }
      
      // Configuration Matrix
      configure { project ->
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('clientName')
          values('CS_PROD_CLIENTS')
        }
        project / 'axes' << 'hudson.matrix.LabelAxis' {
          name('ingestionBucket')
          values('CS_INGESTION_BUCKET')
        }
      }

      // Build Environment
      wrappers {
        maskPasswords()
      }

      // Build Steps
      steps {
        shell('echo "Running Job C"')
        shell('sh /content-studio-v2/cc/scripts/A2_CES_CORP_LO_CSV_LIBRARY_LOADER_MAIN.sh')
      }
    }

    regex('A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD|A11. CONTENT_STUDIO_SUBSCRIPTION_EXTRACTOR_MAIN|A2. CES_CORP_LO_CSV_LIBRARY_LOADER_MAIN') // Include all jobs in the view
  }

  columns {
    status()
    weather()
    name()
    lastSuccess()
    lastFailure()
    lastDuration()
  }
}
