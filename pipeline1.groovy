pipeline {
  agent any // Run on any available Jenkins agent

  stages {
    stage('Build') {
      steps {
        sh './gradlew build', returnStatus: true, label: 'Running Gradle Build', failOnError: true, attachLog: true // Replace with your Gradle build command
      }
    }
    stage('Unit and Integration Tests') {
      steps {
        sh 'mvn test', returnStatus: true, label: 'Running Unit Tests (JUnit)', failOnError: true, attachLog: true // Replace with your unit test command (e.g., mvn test for Maven with JUnit)
        sh './run_integration_tests.sh', returnStatus: true, label: 'Running Integration Tests (Selenium)', failOnError: true, attachLog: true // Replace with your integration test command (e.g., script to run Selenium tests)
      }
      post {
        success {
          mail subject: 'Unit and Integration Tests Status',
            body: 'Unit and integration tests were successful!',
            to: 'vvarshitha20@gmail.com',
            attachLog: true
        }
        failure {
          mail subject: 'Unit and Integration Tests Status',
            body: 'Unit and integration tests failed!',
            to: 'vvarshitha20@gmail.com',
            attachLog: true
        }
      }
    }
    stage('Code Analysis') {
      steps {
        sh 'sonar-scanner -DprojectKey=my-project -Dsonar.projectKey=my-project -Dsonar.sources=. -Dsonar.host.url=http://your-sonarqube-server:9000', returnStatus: true, label: 'Running SonarQube Analysis', failOnError: true, attachLog: true // Replace with your SonarQube scanner command
      }
    }
    stage('Security Scan') {
      steps {
        sh 'zap-cli api new -p 8080 -f openapi.yaml', returnStatus: true, label: 'Initializing OWASP ZAP Scan', failOnError: true, attachLog: true  // Replace with your OWASP ZAP scan command (assuming OpenAPI definition file)
        sh 'zap-cli api scan -t http://your-app-url', returnStatus: true, label: 'Running OWASP ZAP Scan', failOnError: true, attachLog: true // Replace with your OWASP ZAP scan command (target URL)
      }
      post {
        success {
          mail subject: 'Security Scan Status',
            body: 'Security scan passed!',
            to: 'vvarshitha20@gmail.com',
            attachLog: true
        }
        failure {
          mail subject: 'Security Scan Status',
            body: 'Security scan failed!',
            to: 'vvarshitha20@gmail.com',
            attachLog: true
        }
      }
    }
    stage('Deploy to Staging') {
      steps {
        sh 'aws s3 cp --recursive dist/ s3://your-staging-bucket/', returnStatus: true, label: 'Deploying to Staging (AWS S3)', failOnError: true, attachLog: true // Replace with your deployment script (e.g., AWS S3 upload)
      }
    }
    stage('Integration Tests on Staging') {
      steps {
        sh 'curl http://your-staging-app-url/api/v1/health', returnStatus: true, label: 'Health Check on Staging', failOnError: true, attachLog: true // Replace with your integration test on staging environment
      }
    }
    stage('Deploy to Production') {
      steps {
        // Add your production deployment script here (e.g., similar to deploy to staging)
      }
    }
  }
}

