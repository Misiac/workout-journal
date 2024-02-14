<h1 align="center">
 <img src="assets/logo.png" alt="Workout Journal">
</h1>

<h4 align="center">

A fitness tracking application that leverages AI for personalized workout analysis and planning.

</h4>
<p align="center">
<a href="#description">Description</a> •
  <a href="#key-features">Key Features</a> •
  <a href="#how-to-use">How To Use</a> •
  <a href="#how-to-deploy">How To Deploy</a> 

</p>
 <img src="assets/login.jpeg" alt="Login Page">

## Description

Workout Journal is a fitness tracking application designed for fitness enthusiasts and professionals alike. It offers a
user-friendly interface that simplifies the process of logging workout sessions, tracking progress, and analyzing
performance. The application was primarily developed to provide a more convenient and comprehensive way to analyze
workouts on a larger screen, as opposed to the limited view on mobile devices.

Leveraging the power of OpenAI, the application provides in-depth analysis of workout sessions and generates
personalized workout plans tailored to individual fitness goals and preferences.

In addition to its core features, Workout Journal also includes an admin panel for managing exercise types, muscle
groups, and equipment groups, enhancing the flexibility and adaptability of the application.

With secure user authentication powered by Okta, users can rest assured that their data is always safe.

Workout Journal is not intended for commercial use. This project was developed to explore the capabilities of AI in the
context of fitness tracking. It serves as a practical tool for personal use.

## Key Features

* Log Workout Sessions
    - Easily record workout sessions, including exercises performed, duration, and intensity

* Comprehensive Statistics
    - Track your progress with detailed statistics for each workout and overall performance metrics.

* AI Workout Analysis
    - Obtain in-depth analysis of your workout sessions using OpenAI's AI capabilities, including performance insights
      and improvement recommendations

* AI-Generated Personalized Workout Plans
    - Receive customized workout plans based on personal fitness goals, preferences, and other factors.

* User Authentication with Okta
    - Securely authenticate users and manage access to the application with Okta integration

* Admin Panel
    - Administrators have the ability to add new exercise types, muscle groups, and equipment groups. They can also
      associate exercises with these groups for better organization and tracking.

 <img src="assets/page.jpeg" alt="Login Page">

## How To Use

Access the app deployed on ... tbd

## How To Deploy

To clone the repo, use:

```bash
git clone https://github.com/Misiac/workout-journal.git
```

Refer to the [How to Deploy Workout Journal](howToDeploy.md) guide for detailed instructions on deploying the
application.

## What's Left?

- Dark mode
- Exercise list
- Refresh workout name/date in slider
- Cleanup frontend code
- Logo
- Rework navbar
- Data parser from some mobile app
- Backend logs
- More tests coverage

## Built with:

**Backend:**

- Spring Boot, MySQL

**Frontend:**

- TypeScript, React

**Testing:**

- Mockito, JUnit 5

**Services/APIs:**

- Okta, OpenAI

**DevOps:**

- Docker