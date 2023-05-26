import hudson.model.*
import hudson.model.ListView.*

def jobName = 'A1. CES_LIBRARY_CSV_EXTRACTOR_MAIN_FOR_PROD'
def viewName = 'content-studio CC Flow'

// Create a new view
def listView = new ListView(viewName, Hudson.getInstance())
listView.description = 'This is a new CC view'
listView.filterQueue = true
listView.filterExecutors = true

// Specify the columns for the view
listView.columns = [
    new StatusColumn(),
    new WeatherColumn(),
    new JobColumn(),
    new LastSuccessColumn(),
    new LastFailureColumn(),
    new DurationColumn()
]

// Create the multi-configuration job
def job = new FreeStyleProject(Hudson.getInstance(), jobName)
job.description = 'Load the Course details from CES database for given production portal'

// Discard Old Builds
job.addProperty(new hudson.model.BuildDiscarderProperty(new hudson.tasks.LogRotator(numToKeepStr: '4', artifactNumToKeepStr: '4')))

// Source Code Management
job.setScm(new hudson.scm.NullSCM())

// Build Triggers
job.addTrigger(new hudson.triggers.TimerTrigger('H 02 * * 1-5'))

// Build Environment
job.getBuildWrappersList().add(new hudson.tasks.MasqueradePasswordsBuildWrapper())

// Post-Build Actions
job.getPublishersList().add(new hudson.tasks.BuildTrigger('A11. CONTENT_STUDIO_SUBSCRIPTION_EXTRACTOR_MAIN', Result.SUCCESS))

// Add the job to the view
listView.add(job)

// Save the view
Hudson.getInstance().addView(listView)
