job('job-2') {
    description('This is an example DSL job-2 with downstream')

    steps {
        shell('echo "This is a job-2"')
    }

    publishers {
        downstreamParameterized {
            trigger('job-3')
        }
    }
}
