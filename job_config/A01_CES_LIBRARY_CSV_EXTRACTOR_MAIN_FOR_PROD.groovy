import hudson.model.*

def jobName = 'A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD'
def viewName = 'content-studio CC Flow'

// Create a new view
listView(viewName) {
    description('This is a new CC view')
    filterBuildQueue()
    filterExecutors()
}

// Create the multi-configuration job
matrixProject(jobName) {
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

// Add the job to the view
def listView = Jenkins.instance.getView(viewName)
listView.add(Jenkins.instance.getItem(jobName))

// Specify the columns for the view
def jobColumns = ['status', 'weather', 'name', 'lastSuccess', 'lastFailure', 'lastDuration']
listView.configure { view ->
    view / 'columns' {
        jobColumns.each { column ->
            "${column}"()
        }
    }
}
