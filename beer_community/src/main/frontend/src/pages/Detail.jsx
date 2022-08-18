import React, { useEffect, useState } from "react";
import DetailFooter from "../components/detailFooter/detailFooter";
import DetailImgs from "../components/detailImgs/detailImgs";
import DetailInfo from "../components/detailInfo/detailInfo";
import DetailText from "./../components/detailText/detailText";
import Nav from "../components/Nav/Nav";
import axios from "axios";
import { useNavigate, useLocation } from "react-router-dom";
import Footer from './../components/Footer/Footer';

const Detail = (props) => {
  const [imageUrl, setImageUrl] = useState("");
  const [beerName, setBeerName] = useState("");
  const [totalPoint, setTotalPoint] = useState("");
  const [information, setInformation] = useState("");
  const [alcoholDegree, setAlcoholDegree] = useState("");
  const [isLiked, setIsLiked] = useState("");
  const [taste, setTaste] = useState("");
  const [reviewCount, setReviewCount] = useState("");
  const location = useLocation();
  const id = location.state.id;
  let navigate = useNavigate();
  console.log(id);
  useEffect(() => {
    const getDetailBeer = async () => {
      let response = await axios.get(`/api/search/detail/${id}`);
      console.log(response);
      console.log("response.data.data: ", response.data.data);
      console.log("response.data.data[0]: ", response.data.data[0]);
      console.log(response.data.data.imageUrl);
      console.log("isLiked : ", typeof response.data.data.is_liked);
      setImageUrl(response.data.data.imageUrl);
      setBeerName(response.data.data.beerName);
      setTotalPoint(response.data.data.totalPoint);
      setInformation(response.data.data.information);
      setAlcoholDegree(response.data.data.alcoholDegree);
      setIsLiked(response.data.data.is_liked);
      setTaste(response.data.data.taste);
      setReviewCount(response.data.data.count);
    };
    getDetailBeer();
  }, []);
  return (
    <>
      <Nav navigate={navigate} />
      <div>
        <DetailImgs imageUrl={imageUrl} beerName={beerName} />
        <DetailInfo
          alcoholDegree={alcoholDegree}
          taste={taste}
          totalPoint={totalPoint}
        />
        <DetailText information={information} />
        <DetailFooter isLiked={isLiked} id={id} setIsLiked={setIsLiked} reviewCount={reviewCount}/>
        <Footer />
      </div>
    </>
  );
};

export default Detail;
