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
        {Array.isArray(beerData.tastes) &&
          beerData.tastes.map((i,index) => {
            // SWEET, SOUR, BITTER, FLORAL, AROMATIC, FRUITINESS
            // 달콤함, 새콤함, 씁쓸함, 꽃향기, 고소함, 과일향
            if (i === "SWEET") {
              return (
                <button key={index} className={styles.taste}>
                  달콤함
                </button>
              );
            } else if (i === "SOUR") {
              return (
                <button key={index} className={styles.taste}>
                  신맛
                </button>
              );
            } else if (i === "BITTER") {
              return (
                <button key={index} className={styles.taste}>
                  씁쓸함
                </button>
              );
            } else if (i === "FLORAL") {
              return (
                <button key={index} className={styles.taste}>
                  꽃향기
                </button>
              );
            } else if (i === "AROMATIC") {
              return (
                <button key={index} className={styles.taste}>
                  고소함
                </button>
              );
            } else if (i === "FRUITINESS") {
              return (
                <button key={index} className={styles.taste}>
                  과일향
                </button>
              );
            }
          })}
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