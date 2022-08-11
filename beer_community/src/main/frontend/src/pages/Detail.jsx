import React from "react";
import DetailFooter from "../components/detailFooter/detailFooter";
import DetailImgs from "../components/detailImgs/detailImgs";
import DetailInfo from "../components/detailInfo/detailInfo";
import DetailText from "./../components/detailText/detailText";
import { useLocation } from "react-router-dom";
const Detail = (props) => {
  const location = useLocation();
  const id = location.state.id;
  console.log(id);
  return (
    <div>
      <DetailImgs />
      <DetailInfo />
      <DetailText />
      <DetailFooter />
    </div>
  );
};

export default Detail;
