listView('content-studio CC Flow') {
    description('This is a new CC view')
    filterBuildQueue()
    filterExecutors()
  
    matrixProject('A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD') {
        description('Load the Course details from CES database for given production portal')
  
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
  
        // Build Environment
        wrappers {
            maskPasswords()
        }
  
        // Post-Build Actions
        publishers {
            downstream('A11. CONTENT_STUDIO_SUBSCRIPTION_EXTRACTOR_MAIN')
        }
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
