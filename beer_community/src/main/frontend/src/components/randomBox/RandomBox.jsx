import React from "react";
import styles from "./randomBox.module.css";
import { useState, useEffect } from 'react';
import axios from "axios";

const RandomBox = (props) => {
  const [beerData, setBeerData] = useState([]);

  const handleButtonClick= (e) => {
    const getBeerList = async() => {
      let response = await axios.get("/api/recommend");
      setBeerData(response.data.data);
    }
    getBeerList();
  }
  
  useEffect(() => {
    const getBeerList = async() => {
      let response = await axios.get("/api/recommend");
      setBeerData(response.data.data);
    }
    getBeerList();
  }, []);

  return (
    <div className={styles.mainBox}>
      <div className={styles.title}>오늘의 맥주는?</div>
      <img src={beerData.imageUrl} alt="" className={styles.img} />
      <div className={styles.detail}>
        <div className={styles.degree}>
          <p className={styles.text}>Alchol degree</p>
          <p className={styles.degreeNum}>{beerData.alcoholDegree}</p>
        </div>
        <div className={styles.tastes}>
            <button className={styles.taste}>부드러움</button>
            <button className={styles.taste}>부드러움</button>
        </div>
      </div>

      <div className={styles.name}>{beerData.beerName}</div>
      <div className={styles.description}>
        {beerData.information}
      </div>
      <button className={styles.retry} onClick={handleButtonClick}>
        다시하기
      </button>
    </div>
  );
};

export default RandomBox;