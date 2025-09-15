pipeline {
    agent any

    triggers {
        githubPush()
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build') {
            steps {
                echo "Building branch: ${env.BRANCH_NAME}"
                sh 'ls -lah'
            }
        }
        stage('Test') {
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
