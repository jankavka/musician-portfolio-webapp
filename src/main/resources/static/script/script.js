document.addEventListener("DOMContentLoaded", function () {
  let currentLocation = new URL(window.location).pathname;


  /**
   * Sets active point in navigation according to location
   * for CSS
   */
  const setActiveNav = () => {
    // endpoints and id of element which will be set active
    const endpoints = new Map([
      ["/", "home"],
      ["/projekty", "projects"],
      ["/foto", "media"],
      ["/video", "media"],
      ["/koncerty", "concerts"],
      ["/kontakty", "contact"],
      ["/o-me", "about_me"],
      ["/admin", "home"],
      ["/admin/projekty", "projects"],
      ["/admin/foto/admin", "media"],
      ["/admin/video/admin", "media"],
      ["/admin/koncerty", "concerts"],
      ["/admin/kontakty", "contact"],
      ["/admin/o-me", "about_me"],
      ["/admin/profil", "profile"],
    ]);

    // adding css class to element
    for (const [key, value] of endpoints.entries()) {
      if (currentLocation === key) {
        let element = this.getElementById(value);
        element.classList.add("active-nav");
      }
    }
  };

  setActiveNav();

  //Sets inner html content of edited text
  const insertInnerHtml = () => {
    let source = document.getElementsByName("source");
    let target = document.getElementsByName("target");

    for (let i = 0; i < source.length; i++) {
      target[i].innerHTML = source[i].innerText;
    }
  };

  //locations for setting inner html
  const listOfLocations = [
    "/o-me",
    "/projekty",
    "/admin/o-me",
    "/admin/projekty",
  ];

  for (const loc of listOfLocations) {
    if (currentLocation === loc) {
      insertInnerHtml();
    }
  }

  /**
   * This method adds delete functionality to delete buttons on page
   */
  const addDeleteFunctionality = () => {
    const deleteButtons = document.getElementsByName("deleteButton");

    const deleteUrls = new Map([
      ["projekty", "/admin/projekty/vymazat/"],
      ["album", "/admin/foto/vymazat/"],
      ["foto", "/admin/album/vymazat/"],
      ["koncerty", "/admin/koncerty/vymazat/"],
      ["video", "/admin/video/vymazat/"],
    ]);

    const entitiesNames = ["projekt", "album", "foto", "video"];

    const setHref = () => {
      for (const [key, value] of deleteUrls.entries()) {
        if (currentLocation.includes(key)) {
          console.log(value);
          return value;
        }
      }
    };

    const setEntity = () => {
      for (const entity of entitiesNames) {
        if (currentLocation.includes(entity)) {
          return entity;
        }
      }
    };

    //this cycle adds to every delete button from projects its functionality
    for (let button of deleteButtons) {
      button.addEventListener("click", () => {
        let id = button.getAttribute("id");
        let entityName = document.getElementById("entity" + id).innerText;
        let consent = confirm(
          "Opravdu chcete vymazat " + setEntity() + " " + entityName + " " + id
        );
        if (consent) {
          let realDelete = document.createElement("a");
          realDelete.setAttribute("href", setHref() + id);
          document.body.appendChild(realDelete);
          realDelete.click();
          document.body.removeChild(realDelete);
        }
      });
    }
  };

  addDeleteFunctionality();

  /**
   * This method fill form, after file exception on server,
   * with previous values
   */
  const renderFormFields_AfterFileException = () => {
    if (currentLocation === "/admin/album/novy") {
      let nameElement = document.getElementById("name");
      let descElement = document.getElementById("desc");
      let nameValue = localStorage.getItem("nameValue");
      let descValue = localStorage.getItem("descValue");

      nameElement.addEventListener("change", (e) => {
        nameValue = e.target.value;
        localStorage.setItem("nameValue", nameValue);
      });

      descElement.addEventListener("change", (e) => {
        descValue = e.target.value;
        localStorage.setItem("descValue", descValue);
      });

      if (nameValue || descValue) {
        nameElement.value = nameValue;
        descElement.value = descValue;
      }

      let backButton = document.getElementById("back");
      backButton.addEventListener("click", () => {
        localStorage.removeItem("nameValue");
        localStorage.removeItem("descValue");
      });
    }
  };

  renderFormFields_AfterFileException();

  /**
   * Adds logtou functionality to logout button
   */
  const logoutHandler = () => {
    if (
      currentLocation.includes("/admin") &&
      !currentLocation.includes("logout")
    ) {
      const logoutForm = document.getElementById("logout-form");
      const logoutButton = document.getElementById("logout-button");
      const realLogoutButton = document.createElement("input");

      logoutButton.addEventListener("click", () => {
        let consent = confirm("Opravdu se chcete odlásit?");

        if (consent) {
          realLogoutButton.setAttribute("type", "submit");
          logoutForm.appendChild(realLogoutButton);
          realLogoutButton.click();
          logoutForm.removeChild(realLogoutButton);
        }
      });
    }
  };

  logoutHandler();

  /**
   * Handle interaction with YouTube API for embedding videos to page
   */
  handleYoutubeVideos = function () {
    if (currentLocation === "/admin/video" || currentLocation === "/video") {
      // takes elements with link to the video wich is transformed and saved just as an videos id
      let videoIds = document.getElementsByClassName("video");

      // Takes elements with video titles, which is later used as an css selector
      let videoName = document.getElementsByClassName("name");

      //function declared on window object to be globaly available
      window.onYouTubePlayerAPIReady = function () {
        for (let j = 0; j < videoIds.length; j++) {
          var player;
          player = new YT.Player(videoName[j].innerText, {
            height: currentLocation === "/admin/video" ? "100" : "200",
            width: currentLocation === "/admin/video" ? "150" : "350",
            videoId: videoIds[j].innerText,
          });
        }
      };

      var tag = document.createElement("script");
      tag.src = "https://www.youtube.com/player_api";
      document.head.appendChild(tag);
    }
  };
  handleYoutubeVideos();
});
