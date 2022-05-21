const method = function () {
  const inputMetropolis = document.getElementById("inputMetropolis");
  const inputCity = document.getElementById("inputCity");
  const inputTown = document.getElementById("inputTown");

  function deleteDefaultOptionWithId(dropdownId) {
    const options = document.getElementById(`${dropdownId}`).children;
    if (options[0].innerHTML == "선택") {
      options[0].remove();
    }
  }

  function setInputMetropolis() {
    const val = document.getElementById("inputMetropolis").value;

    // move map
    moveMapWithKeyword(val, 1);

    // update this list
    deleteDefaultOptionWithId("inputMetropolis");

    // update sublist
    fetch("assets/districts.json")
      .then((response) => response.json())
      .then((json) => {
        const sublist = Object.keys(json[val]);
        displayDistrict_City(sublist);
      });
    inputTown.innerHTML = "<option>선택</option>";
  }

  function displayDistrict_City(list) {
    let inputCity = document.getElementById("inputCity");

    inputCity.innerHTML = "<option>선택</option>";

    for (let i = 0; i < list.length; i++) {
      let option = document.createElement("option");
      option.innerHTML = list[i];
      inputCity.appendChild(option);
    }
  }

  function setInputCity() {
    let val1 = document.getElementById("inputMetropolis").value;
    let val2 = document.getElementById("inputCity").value;

    // move map
    moveMapWithKeyword(`${val1} ${val2}`, 2);

    // update this list
    deleteDefaultOptionWithId("inputCity");

    fetch("assets/districts.json")
      .then((response) => response.json())
      .then((json) => {
        let sublist = Object.keys(json[val1][val2]);
        displayDistrict_Town(sublist);
      });
  }

  function displayDistrict_Town(list) {
    let inputTown = document.getElementById("inputTown");
    inputTown.innerHTML = "<option>선택</option>";
    for (let i = 0; i < list.length; i++) {
      let option = document.createElement("option");
      option.innerHTML = list[i];
      inputTown.appendChild(option);
    }
  }

  function setInputTown() {
    let val1 = document.getElementById("inputMetropolis").value;
    let val2 = document.getElementById("inputCity").value;
    let val3 = document.getElementById("inputTown").value;

    // move map
    moveMapWithKeyword(`${val1} ${val2} ${val3}`, 3);

    // update this list
    deleteDefaultOptionWithId("inputTown");
  }

  function moveMapWithAddress(keyword) {
    geocoder.addressSearch(keyword, function (result, status) {
      // 정상적으로 검색이 완료됐으면
      if (status === kakao.maps.services.Status.OK) {
        var coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        // 지도의 중심을 결과값으로 받은 위치로 이동시킵니다
        map.setCenter(coords);
      }
    });
  }

  function moveMapWithKeyword(keyword, opt) {
    geocoder.addressSearch(keyword, function (result, status) {
      if (status === kakao.maps.services.Status.OK) {
        let coords = new kakao.maps.LatLng(result[0].y, result[0].x);
        map.setCenter(coords);
        if (opt == 1) {
          map.setLevel(8);
        } else if (opt == 2) {
          map.setLevel(6);
        } else if (opt == 3) {
          map.setLevel(4);
        }
      }
    });
  }
};
const map_dropdown_js = { method };
export default map_dropdown_js;
