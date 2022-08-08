import React, { useState } from "react";
import BeerList from "../components/beerList/beerList";
import BeerSearch from "../components/beerSearch/beerSearch";
import Footer from "../components/Footer/Footer";
import FilterBox from "./../components/filterBox/filterBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import data from "../db/data.json";

const Search = (props) => {
    const [beerData, setBeerData] = useState(data);
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <BeerSearch />
      <FilterBox />
      <BeerList data={beerData} />
      <Footer />
    </>
  );
};
export default Search;
