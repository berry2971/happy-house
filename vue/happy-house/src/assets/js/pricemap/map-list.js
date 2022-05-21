const happyHouseExternalList = document.getElementById("map_result");

  function displayExternalList(data, pagination) {
    // result: 아파트목록
    let happyHouseIndexMapExternalListItems = convertToHappyItem(data);
    let happyHouseExternalList = document.getElementById("map_result");
    happyHouseExternalList.innerHTML = "";

    for (let i = 0; i < happyHouseIndexMapExternalListItems.length; i++) {
      let curr = happyHouseIndexMapExternalListItems[i]; // 아파트 객체
      /* curr 단일 객체 생김새
            {
                "apt_name": "쌍용플래티넘밸류아파트",
                "address_road": "서울 강남구 테헤란로4길 46",
                "address_legacy": "서울 강남구 역삼동 826-37",
                "x": "127.03040567183359",
                "y": "37.49539984153658"
            }
            */

      let callback = function (result, status) {
        if (status === kakao.maps.services.Status.OK) {
          afterDisplayExternalList(result, curr, pagination);
        } else {
          console.log(status);
        }
      };

      const coord = new kakao.maps.LatLng(curr["y"], curr["x"]);
      geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
    }
  }

  function afterDisplayExternalList(result, curr, pagination) {
    const result_address = result[0]["address"];
    const addrLv1 = result_address["region_1depth_name"];
    const addrLv2 = result_address["region_2depth_name"];
    const addrLv3 = result_address["region_3depth_name"];
    const bunji_main = result_address["main_address_no"];
    const bunji_sub = result_address["sub_address_no"];

    const urlBase = "http://localhost:8080/deal?action=price";
    fetch(
      `${urlBase}&addrLv1=${addrLv1}&addrLv2=${addrLv2}&addrLv3=${addrLv3}&bunjiMain=${bunji_main}&bunjiSub=${bunji_sub}`
    )
      .then((resp) => {
        return resp.json();
      })
      .then(function (resp) {
        return resp;
      })
      .then(function (resp) {
        console.log(resp);

        let singleItem = document.createElement("div"); // <div> 태그
        singleItem.setAttribute("class", "col-sm-8");

        // 아파트명
        let liDiv1 = document.createElement("div");
        liDiv1.setAttribute("class", "extList_aptName");
        liDiv1.setAttribute("class", "indexMapExternalList_aptName");
        liDiv1.setAttribute("style", "font-size:15px;");
        liDiv1.innerText = curr["apt_name"];

        // 구주소
        let liDiv2 = document.createElement("div");
        liDiv2.setAttribute("class", "extList_addressLegacy");
        liDiv2.setAttribute("class", "indexMapExternalList_addressLegacy");
        liDiv2.setAttribute("style", "color:grey");
        liDiv2.innerText = curr["address_legacy"];

        // 신주소
        let liDiv3 = document.createElement("div");
        liDiv3.setAttribute("class", "extList_addressRoad");
        liDiv3.setAttribute("class", "indexMapExternalList_addressRoad");
        liDiv3.setAttribute("style", "color:grey");
        liDiv3.innerText = curr["address_road"];

        singleItem.appendChild(liDiv1);
        singleItem.appendChild(liDiv2);
        singleItem.appendChild(liDiv3);

        // 실거래가
        let tmpDiv = document.createElement("div");
        for (let tt = 0; tt < resp.length; tt++) {
          let aptPrice = Number(resp[tt]["price"]);
          let aptPrice1000m = aptPrice / 10000;
          tmpDiv.innerHTML += `${aptPrice1000m}억원<br>`;
          singleItem.appendChild(tmpDiv);
        }

        const addrLv1r = resp[0]["addrLv1"];
        const addrLv2r = resp[0]["addrLv2"];
        const addrLv3r = resp[0]["addrLv3"];
        const bunji_mainr = resp[0]["bunjiMain"];
        const bunji_subr = resp[0]["bunjiSub"];
        singleItem.addEventListener("click", () => {
          console.log("bunjiMain 찍어보기 ..... : " + bunji_mainr);
          location.href = `deal?action=view-detail&addrLv1=${addrLv1r}&addrLv2=${addrLv2r}&addrLv3=${addrLv3r}&bunjiMain=${bunji_mainr}&bunji_sub=${bunji_subr}`;
        });

        // let targetAptName = curr["apt_name"];
        // let targetAddressLegacy = curr["address_legacy"];
        // let targetAddressRoad = curr["address_road"];
        // singleItem.addEventListener("click", () => {
        //     moveToDetailPage(targetAptName, targetAddressLegacy, targetAddressRoad);
        // });

        // // append load msg
        // const extLstLdMsg = document.createElement("div");
        // extLstLdMsg.setAttribute("class", "extLstLdMsg");
        // extLstLdMsg.innerHTML = "최근 1개월 간 실거래 가격을 불러오고 있습니다.";
        // singleItem.appendChild(extLstLdMsg);

        happyHouseExternalList.appendChild(singleItem);
      });
  }

  function moveToDetailPage(aptName, addressLegacy, addressRoad) {
    sessionStorage.setItem("detail-apt-name", aptName);
    sessionStorage.setItem("detail-apt-address-legacy", addressLegacy);
    sessionStorage.setItem("detail-apt-address-road", addressRoad);
    initDetail();
  }

  /**
   *
   * @param data=kakao_search_res_items
   * @returns []=happyHouseIndexMapExternalListItems
   */
  function convertToHappyItem(data) {
    let ret = [];
    for (let i = 0; i < data.length; i++) {
      let currData = data[i];

      let happyHouseIndexMapExternalListSingleItem = {};
      happyHouseIndexMapExternalListSingleItem["apt_name"] =
        currData["place_name"];
      happyHouseIndexMapExternalListSingleItem["address_road"] =
        currData["road_address_name"];
      happyHouseIndexMapExternalListSingleItem["address_legacy"] =
        currData["address_name"];
      happyHouseIndexMapExternalListSingleItem["x"] = currData["x"];
      happyHouseIndexMapExternalListSingleItem["y"] = currData["y"];

      ret.push(happyHouseIndexMapExternalListSingleItem);
    }
    return ret;
  }

  // data example
  /*

[
    {
        "address_name": "제주특별자치도 제주시 월평동 747",
        "category_group_code": "",
        "category_group_name": "",
        "category_name": "부동산 > 주거시설 > 아파트",
        "distance": "525",
        "id": "79756663",
        "phone": "064-759-8114",
        "place_name": "제주첨단과학기술단지꿈에그린3차아파트",
        "place_url": "http://place.map.kakao.com/79756663",
        "road_address_name": "제주특별자치도 제주시 첨단로동길 106",
        "x": "126.57630886801763",
        "y": "33.45048486992367"
    },
    {
        "address_name": "제주특별자치도 제주시 월평동 717",
        "category_group_code": "",
        "category_group_name": "",
        "category_name": "부동산 > 주거시설 > 아파트",
        "distance": "820",
        "id": "455898711",
        "phone": "",
        "place_name": "JDC제주첨단리슈빌아파트",
        "place_url": "http://place.map.kakao.com/455898711",
        "road_address_name": "제주특별자치도 제주시 첨단로동길 24",
        "x": "126.57403723831578",
        "y": "33.45754203445571"
    }
]
     */
