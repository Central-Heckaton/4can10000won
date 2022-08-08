import React, { useState } from "react";
import LikeBox from "../components/LikeBox/LikeBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
//test data
import data from "../db/data.json";
const Like = () => {
  const [beerData,setBeerData] = useState(data)
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <LikeBox data={beerData}/>
      <Footer />
    </>
  );
};

export default Like;
