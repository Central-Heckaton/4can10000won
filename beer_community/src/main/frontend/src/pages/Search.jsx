import React, { useState, useEffect } from "react";
import BeerList from "../components/beerList/beerList";
import BeerSearch from "../components/beerSearch/beerSearch";
import Footer from "../components/Footer/Footer";
import FilterBox from "./../components/filterBox/filterBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import axios from 'axios';
import data from "../db/data.json";

const Search = (props) => {
  const [beerData, setBeerData] = useState();
  let navigate = useNavigate();
  useEffect(() => {
      axios.get('/api/search')
      .then((response) => {
          setBeerData(response.data);
      })
    }, []);
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
