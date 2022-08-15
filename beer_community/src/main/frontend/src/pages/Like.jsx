import React, { useState, useEffect } from "react";
import LikeBox from "../components/LikeBox/LikeBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import axios from "axios";

const Like = () => {
  const getBeerData = async () => {
    const response = await axios.get("/api/likebeers");
    console.log(response.data);
    setBeerData(response.data.data);
  };
  useEffect(() => {
    getBeerData();
    console.log(beerData);
  },[]);
  const [beerData, setBeerData] = useState([]);
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <LikeBox data={beerData} />

      <Footer />
    </>
  );
};

export default Like;
