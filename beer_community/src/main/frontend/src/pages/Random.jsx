import React, { useState, useEffect } from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import RandomLoading from "../components/randomLoading/RandomLoading";
import RandomBox from "../components/randomBox/RandomBox";
import axios from "axios";

const Random = () => {
  let navigate = useNavigate();
  const [randomLoading, setRandomLoading] = useState(true);
  const [beerData, setBeerData] = useState([]);

  useEffect(() => {
    const timer = setTimeout(() => {
      setRandomLoading(false);
    }, 1000);

    const getBeerList = async() => {
      let response = await axios.get("/api/recommend");
      console.log(response.data.data);
      setBeerData(response.data.data);
    }
    getBeerList();

    return () => {
      clearTimeout(timer);
    }
    
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
          <RandomBox data={beerData}/>
          <Footer />
        </>
      )}
    </>
  );
};

export default Random;