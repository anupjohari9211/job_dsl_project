// Load the job creation configuration
def jobConfig = load('job_config/*.groovy)

// Load the axes configuration
def axesConfig = load('axes/axes_config.groovy')

// Load the build steps
def buildSteps = load('build_steps/build_steps.groovy')

// Create the multi-configuration job
jobConfig.call()

// Modify the multi-configuration job with axes and build steps
matrixProject('Multi-Job Configuration') {
    axes {
        axesConfig.call()
    }
    steps {
        buildSteps.call()
    }
}
