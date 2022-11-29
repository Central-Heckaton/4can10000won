// Review.jsx
import React from "react";
import ReviewButton from "../components/ReviewButton/ReviewButton";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import ReviewBox from "../components/ReviewBox/ReviewBox";
import { useLocation } from "react-router-dom";
import Footer from "../components/Footer/Footer";

const Review = () => {
  let navigate = useNavigate();
  const location = useLocation();
  const beerId = location.state.id;

  return (
    <>
      <Nav navigate={navigate} />
      <ReviewButton />
      <ReviewBox beerId={beerId} />
      <Footer />
    </>
  );
};

export default Review;
