import React, { useState, useEffect } from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import RandomLoading from "../components/randomLoading/RandomLoading";
import RandomBox from "../components/randomBox/RandomBox";
import axios from 'axios';
const Random = () => {
  let navigate = useNavigate();
  const [randomLoading, setRandomLoading] = useState(true);
  useEffect(() => {
//    const timer = setTimeout(() => {
//      setRandomLoading(false);
//    }, 1000);
    const getBeerList = async () => {
        setRandomLoading(false);
        console.log('enter getBeerList');
        let response = await axios.get("/api/recommend");
        console.log('resp: ', response);
    }
//    clearTimeout(timer);
    getBeerList();
  }, []);

  return (
    <>
      {randomLoading === true ? (
        <>
          <RandomLoading />
        </>
      ) : (
        <>
          <Nav navigate={navigate} />
          <RandomBox />
          <Footer />
        </>
      )}
    </>
  );
};

export default Random;
