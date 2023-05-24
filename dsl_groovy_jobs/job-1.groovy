job('job-1') {
    description('This is an example DSL job with build steps and downstream job')

    steps {
        shell('echo "This is a first job"')
    }

    publishers {
        downstreamParameterized {
            trigger('job-2')
        }
    }
}
