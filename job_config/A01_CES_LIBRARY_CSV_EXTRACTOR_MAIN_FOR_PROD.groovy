import hudson.model.*
import hudson.model.ListView.*

def jobName = 'A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD'
def viewName = 'content-studio CC Flow'

// Create a new view
listView(viewName) {
    description('This is a new CC view')
    filterBuildQueue()
    filterExecutors()
  
    // Specify the columns for the view
    columns {
        status()
        weather()
        name()
        lastSuccess()
        lastFailure()
        lastDuration()
    }
}

// Create the multi-configuration job
job(jobName) {
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
def listView = Jenkins.instance.getView(viewName)
listView.add(Jenkins.instance.getItem(jobName))
