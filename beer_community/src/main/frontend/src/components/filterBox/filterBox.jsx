import React, { useCallback, useEffect } from "react";
import styles from "./filterBox.module.css";
import { useState } from "react";
import axios from "axios";
import BeerList from "../beerList/beerList";
// import BeerList from "../components/beerList/beerList";

const FilterBox = () => {
  const [beerData, setBeerData] = useState([]);
  const [beerName, setBeerName] = useState();
  const [flag, setFlag] = useState(0);

  const [filterState, setFilterState] = useState({
    passingTags: {
      Lager: false,
      Ale: false,
      IPA: false,
      Stout: false,
    },
  });

  useEffect(() => {
    console.log("Enter UseEffect");
    if (flag === 0) {
      getBeerList();
    } else if (flag === 1) {
      getSearch();
      setFlag(0);
    }
  }, [filterState]);
  const handleFilter = (e) => {
    const beerType = e.target.id;
    setFilterState({
      passingTags: {
        ...filterState.passingTags,
        [beerType]: !filterState.passingTags[beerType],
      }, // 클릭하면 true, false로 바꿔주기
    });
    // console.log(filterState.passingTags[beerType]);
    if (filterState.passingTags[beerType] === false) {
      let cur = document.getElementById(beerType);
      cur.style.color = "white";
      cur.style.backgroundColor = "#FDC74B";
    }

    if (filterState.passingTags[beerType] === true) {
      let cur = document.getElementById(beerType);
      cur.style.color = "#FDC74B";
      cur.style.backgroundColor = "white";
    }
  };

  const filteredCollected = useCallback(() => {
    const clicked = [];
    const now = filterState.passingTags;

    for (let item in now) {
      if (now[item]) {
        // clicked.now = item; -> 현재 클릭된 아이템
        clicked.push(item);
      }
    }

    return clicked;
  }, [filterState.passingTags]);

  const result = filteredCollected();
  console.log(result);

  const getBeerList = async (e) => {
    console.log("GetBeerList Called");
    const request_data = { beerTypeList: result }; // type of -> Object
    let response = await axios({
      method: "post",
      url: "/api/filter",
      headers: { "Content-Type": "application/json" },
      data: JSON.stringify(request_data),
    });

    console.log("response: ", response);
    setBeerData(response.data.data);
  };
  // response.data
  // [{ }, { }, { } ...] -> props로 전달필요

  const resetColor = () => {
    const reset = filterState.passingTags;
    for (let item in reset) {
      document.getElementById(item).style.color = "#FDC74B";
      document.getElementById(item).style.backgroundColor = "white";
    }
  };

  const getSearch = async () => {
    console.log("beerSearch.jsx/clicked!");
    let url = "/api/beername-search/" + beerName;
    console.log("url: ", url);
    let response = await axios.get(url);
    console.log("response: ", response);
    setBeerData(response.data.data);
  };
  const resetState = () => {
    setFilterState({
      passingTags: {
        Lager: false,
        Ale: false,
        IPA: false,
        Stout: false,
      },
    });
  };
  const handleSearchBtnClick = async (e) => {
    e.preventDefault();
    console.log("beername: ", beerName);
    if (beerName === "" || beerName === undefined) {
      resetColor();
      resetState();
    } else {
      await setFlag(1);
      resetColor();
      resetState();
    }
  };
  return (
    <>
      <div className={styles.searchContainer}>
        <input
          name="beerName"
          className={styles.input}
          type="text"
          value={beerName || ""}
          onChange={(e) => setBeerName(e.target.value)}
        />
        <button className={styles.search} onClick={handleSearchBtnClick}>
          <img src="/img/search.png" alt="search img" />
        </button>
      </div>

      <div className={styles.container}>
        <button id="Lager" className={styles.box} onClick={handleFilter}>
          라거
        </button>
        <button id="Ale" className={styles.box} onClick={handleFilter}>
          에일
        </button>
        <button id="IPA" className={styles.box} onClick={handleFilter}>
          IPA
        </button>
        <button id="Stout" className={styles.box} onClick={handleFilter}>
          스타우트
        </button>
      </div>
      <BeerList data={beerData} />
    </>
  );
};

export default FilterBox;
