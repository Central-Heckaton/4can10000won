import React from "react";
import Footer from "../components/Footer/Footer";
import FilterBox from "./../components/filterBox/filterBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";


const Search = (props) => {
  let navigate = useNavigate();
  return (
    <>
      <Nav navigate={navigate} />
      <FilterBox />
      {/*<BeerList data={beerData}/>*/}
      <Footer />
    </>
  );
};
export default Search;
