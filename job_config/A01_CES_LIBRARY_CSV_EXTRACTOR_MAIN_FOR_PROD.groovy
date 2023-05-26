import hudson.model.*
import javaposse.jobdsl.dsl.*

def jobName = 'A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD'
def viewName = 'content-studio CC Flow'

// Create a new view
pipelineJob(viewName) {
    description('This is a new CC view')

    definition {
        cps {
            script(readFileFromWorkspace('path/to/pipeline_script.groovy'))
        }
    }
}

// Create the multi-configuration job
jobDsl(jobName) {
    description('Load the Course details from CES database for given production portal')

    // Discard Old Builds
    wrappers {
        buildDiscarder(logRotator(numToKeepStr: '4', artifactNumToKeepStr: '4'))
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

// Add the job to the view
view(viewName) {
    jobColumns.each { column ->
        column(column)
    }
    job(jobName)
}
