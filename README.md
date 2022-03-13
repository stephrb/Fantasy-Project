# Fantasy Basketball Analytics
<!-- TABLE OF CONTENTS -->
<details>
  <summary>Table of Contents</summary>
  <ol>
    <li>
      <a href="#about-the-project">About The Project</a>
      <ul>
        <li><a href="#technologies">Technologies</a></li>
      </ul>
    </li>
    <li>
      <a href="#getting-started">Getting Started</a>
      <ul>
        <li><a href="#prerequisites">Prerequisites</a></li>
        <li><a href="#installation">Installation</a></li>
      </ul>
    </li>
    <li><a href="#usage">Usage</a></li>
    <li><a href="#roadmap">Roadmap</a></li>
    <li><a href="#contributing">Contributing</a></li>
    <li><a href="#license">License</a></li>
    <li><a href="#contact">Contact</a></li>
    <li><a href="#acknowledgments">Acknowledgments</a></li>
  </ol>
</details>



<!-- ABOUT THE PROJECT -->
## About The Project
![Product Name Screen Shot](fba-screenshots/title.png?raw=true)
<p> Fantasy Analytics aids users in analysis of their ESPN Fantasy Basketball league. Users can speculate who will make the playoffs based on matchup decisions from the user, observe power rankings, see score projections based on player averages over a given time period and set whether injuries should be accounted for, compare their schedule to other members of the league, and easily see the NBA games scheduled for a given week to determine player pickups. </p>
<p align="right">(<a href="#top">back to top</a>)</p>


### Technologies

* [React.js](https://reactjs.org/)
* [Java Spring Boot](https://spring.io/projects/spring-boot)
* [NBA Schedule API](https://fantasy.espn.com/apis/v3/games/fba/)
* [ESPN Fantasy API](https://write.corbpie.com/using-the-nba-schedule-api-with-php/)
* [Maven](https://maven.apache.org/)


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- GETTING STARTED -->
## Getting Started

This is an example of how you may give instructions on setting up your project locally.
To get a local copy up and running follow these simple example steps.

### Prerequisites

* npm
  ```sh
  npm install npm@latest -g
  ```
 * Maven

### Installation

1. Clone the repo
   ```sh
   git clone https://github.com/stephrb/Fantasy-Project.git
   ```
2. Install NPM packages
   ```sh
   npm install
   ```

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- USAGE EXAMPLES -->
## Usage
### Running Locally
1. Run Main in fba-backend
2. Run frontend
 ```sh
   cd fba-frontend
   npm start
   ```
3. Navigate to [http://localhost:3000/](http://localhost:3000/)
4. Type in your ESPN League ID or try out a demo with a sample league!

### Screen Shots
#### League ID Popup
![Alt text](fba-screenshots/popup.png?raw=true "Popup")
#### Home Page
![Alt text](fba-screenshots/homepage.png?raw=true "Home Page")
#### Power Rankings
![Alt text](fba-screenshots/powerrankings.png?raw=true "Power Rankings")
#### Schedule Comparisons
![Alt text](fba-screenshots/comparison.png?raw=true "Schedule Comparisons")
#### Playoff Machine
![Alt text](fba-screenshots/playoffmachine.png?raw=true "Playoff Machine")
#### Matchup Projections
![Alt text](fba-screenshots/projections.png?raw=true "Matchup Projections")
#### NBA Weekly Games
![Alt text](fba-screenshots/nbagames.png?raw=true "NBA Weekly Games")


<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ROADMAP -->
## Roadmap

- [x] Playoff Machine
- [ ] Waiver Wire Helper
- [ ] Player Streaming Helper
- [ ] Host Website

See the [open issues](https://github.com/stephrb/Fantasy-Projec/issues) for a full list of proposed features (and known issues).

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTRIBUTING -->
## Contributing

Contributions are what make the open source community such an amazing place to learn, inspire, and create. Any contributions you make are **greatly appreciated**.

If you have a suggestion that would make this better, please fork the repo and create a pull request. You can also simply open an issue with the tag "enhancement".
Don't forget to give the project a star! Thanks again!

1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- LICENSE -->
## License

Distributed under the GNU General Public License. See `LICENSE.txt` for more information.

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- CONTACT -->
## Contact

Stephen Buck - stephenbuck@unc.edu

Project Link: [https://github.com/stephrb/Fantasy-Project/](https://github.com/stephrb/Fantasy-Project/)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- ACKNOWLEDGMENTS -->
## Acknowledgments

* [The ESPN fantasy API documentation is very limited, so this was very helpful](https://github.com/cwendt94/espn-api)

<p align="right">(<a href="#top">back to top</a>)</p>



<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[product-screenshot]: images/screenshot.png
