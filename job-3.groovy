job('job-3') {
    description('This is an example DSL job-3 with downstream')

    steps {
        shell('df -h"')
    }

    publishers {
        downstreamParameterized {
            trigger('job-4')
        }
    }
}

