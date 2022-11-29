import React from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import ContentBox from "../components/ContentBox/ContentBox";

const HowToDrink = () => {
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <ContentBox />

      <Footer />
    </>
  );
};

export default HowToDrink;
