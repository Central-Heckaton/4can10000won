import React from "react";
import Footer from "./../components/Footer/Footer";
import BeerTypeFilter from "./../components/beerTypeFilter/beerTypeFilter";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";

const BeerType = (props) => {
  let navigate = useNavigate();
  return (
    <div>
      <Nav navigate={navigate} />
      <BeerTypeFilter />
      <Footer />
    </div>
  );
};

export default BeerType;
