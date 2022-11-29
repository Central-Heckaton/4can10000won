import React, { useState, useEffect } from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import RandomLoading from "../components/randomLoading/RandomLoading";
import RandomBox from "../components/randomBox/RandomBox";

const Random = () => {
  let navigate = useNavigate();
  const [randomLoading, setRandomLoading] = useState(true);

  useEffect(() => {
    const timer = setTimeout(() => {
      setRandomLoading(false);
    }, 1000);

    return () => {
      clearTimeout(timer);
    }
  
  }, []);

  return (
    <>
      {randomLoading === true ? (
        <>
          <RandomLoading />
        </>
      ) : (
        <>
          <Nav navigate={navigate} />
          <RandomBox />
          <Footer />
        </>
      )}
    </>
  );
};

export default Random;