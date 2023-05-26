pipeline {
    agent any

    stages {
        stage('Create View') {
            steps {
                script {
                    def viewName = 'content studio CC flow'

                    // Create the view
                    try {
                        // Check if the view already exists
                        if (Jenkins.instance.getView(viewName) != null) {
                            error("View '${viewName}' already exists.")
                        }

                        // Create the new view
                        listView(viewName)
                    } catch (Exception e) {
                        error("Failed to create the view '${viewName}': ${e.getMessage()}")
                    }
                }
            }
        }
    }
}

