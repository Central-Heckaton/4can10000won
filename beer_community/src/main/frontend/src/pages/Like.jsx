import React, { useState, useEffect } from "react";
import LikeBox from "../components/LikeBox/LikeBox";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import axios from "axios";

const Like = () => {
  const [loading, setLoading] = useState(false);
  const [beerData, setBeerData] = useState([]);
  let navigate = useNavigate();

  const getBeerData = async () => {
  try{
    let response = await axios.get("/api/likebeers");
    console.log('response: ', response);
    setBeerData(response.data.data);
    setLoading(true);
    return
  } catch (error) {
    console.log('error: ', error)
    console.log('error.response: ', error.response)
    console.log('error.response.data: ', error.response.data)
    console.log('error.response.status: ', error.response.status)
    if (error.response.status == 403){ // Forbidden - 로그인 되지 않은 유저의 접근
        alert("내가 찜한 맥주리스트를 확인하기 위해선 로그인이 필요합니다.\n로그인 페이지로 이동합니다")
        navigate("/", {});
    }
  }

  };
  useEffect(() => {
    getBeerData();
    console.log(beerData);
  }, []);

  return (
    <>
      <Nav navigate={navigate} />
      <LikeBox data={beerData} loading={loading} />

      <Footer />
    </>
  );
};

export default Like;
