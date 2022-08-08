import React from "react";
import ContentBox from "../components/LikeBox/LikeBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
const HowToDrink = () => {
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <ContentBox></ContentBox>
      <Footer />
    </>
  );
};

export default HowToDrink;
