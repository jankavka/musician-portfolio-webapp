document.addEventListener("DOMContentLoaded", function () {
  let currentLocation = new URL(window.location).pathname;

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
    ["/projekty/admin", "projects"],
    ["/foto/admin", "media"],
    ["/video/admin", "media"],
    ["/koncerty/admin", "concerts"],
    ["/kontakty/admin", "contact"],
    ["/o-me/admin", "about_me"],
  ]);

  // adding css class to element
  for (const [key, value] of endpoints.entries()) {
    if (currentLocation === key) {
      let element = this.getElementById(value);
      element.classList.add("active-nav");
    }
  }

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
    "/o-me/admin",
    "/projekty/admin",
  ];

  for (const loc of listOfLocations) {
    if (currentLocation === loc) {
      insertInnerHtml();
    }
  }

  const deleteProjectButtons = document.getElementsByName("deleteProject");

  //this cycle adds to every delete button its functionality
  for (let button of deleteProjectButtons) {
    button.addEventListener("click", () => {
      let id = button.getAttribute("id");
      let projectName = document.getElementById("projectName" + id).innerText;
      let consent = confirm("Opravdu chcete vymazat projekt " + projectName);
      if (consent) {
        let realDelete = document.createElement("a");
        realDelete.setAttribute("href", "/projekty/vymazat/" + id);
        document.body.appendChild(realDelete);
        realDelete.click();
        document.body.removeChild(realDelete);
      }
    });
  }
});
