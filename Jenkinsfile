// pipeline {
//     agent any

//     triggers {
//         githubPush()
//     }

//     stages {
//         stage('Checkout') {
//             steps {
//                 checkout scm
//             }
//         }
//         stage('Build') {
//             steps {
//                 echo "Building branch: ${env.BRANCH_NAME}"
//                 sh 'pwd'
//                 sh 'ls -lah'
//                 sh 'echo Hello'
//             }
//         }
//         stage('Test') {
//             steps {
//                 echo 'Testing..'
//                 withCredentials([string(credentialsId: 'secret', variable: 'secret')]) {
//                     sh '''
//                         echo "The secret is ${secret}" > secret.txt
//                     '''
//                 }
//             }
//         }
//         stage('Deploy') {
//             when {
//                 branch 'main'
//             }
//             steps {
//                 echo 'Deploying....'
//             }
//         }
//     }
//     post {
//         always {
//             echo 'Clean Workspace after build'
//             cleanWs()
//         }
//     }
// }

pipeline {
  agent any

  stages {
    stage('Build') {
      steps {
        echo "Building branch ${env.BRANCH_NAME}"
        // e.g. sh 'mvn clean compile'
      }
    }

    stage('PR Validations') {
      when { changeRequest() }
      steps {
        echo "Running PR validations for PR #${env.CHANGE_ID}"
        // e.g. sh 'npm run lint'
      }
    }

    stage('Test') {
      steps {
        echo "Testing on ${env.BRANCH_NAME}"
        // e.g. sh 'mvn test'
      }
    }

    stage('Deploy') {
      when { not { changeRequest() } }
      steps {
        echo "Deploying branch ${env.BRANCH_NAME}"
        // e.g. sh 'ansible-playbook deploy.yml'
      }
    }
  }

  post {
    always {
      echo 'Cleaning workspace'
      cleanWs()
    }
    success {
      echo "✅ ${env.BRANCH_NAME} succeeded"
    }
    failure {
      echo "❌ ${env.BRANCH_NAME} failed"
    }
  }
}
