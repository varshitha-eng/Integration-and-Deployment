pipeline {
    agent any  // Run on any available Jenkins agent

    stages {
        stage('Build') {
            steps {
                echo 'Building code using Gradle...' 
            }
        }
        stage('Unit and Integration Tests') {
            steps {
                echo 'Running unit tests with JUnit...' // Replace with your test command
                // Example tool: JUnit, TestNG, etc.
                echo 'Running integration tests with Selenium...'  // Replace with your command
                // Example tool: Selenium, Cypress, etc.
            }
            post {
                success {
                    mail to:"vvarshitha20@gmail.com",
                    subject:"Build Status Email",
                    body:"Build was successful!"
                    attachLog:true
                    
                }
                failure {
                    mail to:"vvarshitha20@gmail.com",
                    subject:"Build Status Email",
                    body:"Build was successful!"
                    attachLog:true
                }
            }
        }
        stage('Code Analysis') {
            steps {
                echo 'Analyzing code with SonarQube...' // Replace with your command
                // Example tool: SonarQube,  PMD, etc.
            }
        }
        stage('Security Scan') {
            steps {
                echo 'Scanning code for vulnerabilities with SAST scanner...' // Replace with your command
                // Example tool: SAST scanner like OWASP ZAP, Snyk, etc.
            }
            post {
                success {
                    mail to:"vvarshitha20@gmail.com",
                    subject:"Build Status Email",
                    body:"Build was successful!"
                    attachLog:true
                }
                failure {
                    mail to:"vvarshitha20@gmail.com",
                    subject:"Build Status Email",
                    body:"Build was successful!"
                    attachLog:true 
                }
            }
        }
        stage('Deploy to Staging') {
            steps {
                echo 'Deploying application to staging server (e.g., AWS EC2)...' // Replace with your deployment command
            }
        }
        stage('Integration Tests on Staging') {
            steps {
                echo 'Running integration tests on staging environment...' // Replace with your command
            }
        }
        stage('Deploy to Production') {
            steps {
                echo 'Deploying application to production server (e.g., AWS EC2)...' // Replace with your command
            }
        }
    }
}
