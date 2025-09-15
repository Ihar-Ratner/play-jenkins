pipeline {
    agent any

    triggers {
        // Requires GitHub plugin
        githubPush()
    }


    stages {
        stage('Build') {
            when {
                branch 'add_play_pipeline'
            }
            steps {
                echo 'Building..'
                echo 'One more Building..'
                sh 'ls -lah'
                //sh 'cat 1.txt'
                sh 'env'
                //input message: 'Finished using the web site? (Click "Proceed" to continue)'
            }
        }
        stage('PR Lint') {
            when { changeRequest() }
            steps {
                echo "This runs only on PRs"
            }
        }
        stage('Test') {
            when {
                branch 'add_play_pipeline'
            }
            steps {
                echo 'Testing..'
                withCredentials([string(credentialsId: 'secret', variable: 'secret')]) {
                    sh '''
                        echo "The secret is ${secret}" > secret.txt
                    '''
                }
            }
        }
        stage('Deploy') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying....'
            }
        }
    }
    post {
        always {
            echo 'Clean Workspace after build'
            cleanWs()
        }
    }
}