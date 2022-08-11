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
    const [beerData, setBeerData] = useState([]);
    let navigate = useNavigate();
  useEffect (() => {
    const getBeerList = async () => {
          console.log('Search.jsx');
           let response = await axios.get('/api/search');
           console.log('response.data.data: ', response.data.data); // [{}, {}, {}]
           console.log('response.data.data[0]: ', response.data.data[0]);
    //                {id: 1, imageUrl: 'https://res.cloudinary.com/ratebeer/image/upload/d_beer_img_default.png,f_auto/beer_271470', beerName: '클라우드 오리지널 그래비티', totalPoint: 2.48, information: '100% 몰트 리얼 맥주. 오리지널 그래비티. 풍부하고 부드러운.\n클라우드는 유럽산 홉과 독일산 효모를 사용한 프리미엄 맥주입니다.'}
           setBeerData(response.data.data);
        }
       getBeerList(); // 함수호출
    }, []);
  return (
    <>
      <Nav navigate={navigate} />
      <BeerSearch />
      <FilterBox />
      <BeerList data={beerData}/>
      <Footer />
    </>
  );
};
export default Search;
