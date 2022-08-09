import React, { useState ,useEffect} from "react";
import Nav from "../components/Nav/Nav";
import { useNavigate } from "react-router-dom";
import Footer from "../components/Footer/Footer";
import RandomLoading from "../components/randomLoading/RandomLoading";
const Like = () => {
  let navigate = useNavigate();
  const [randomLoading, setRandomLoading] = useState(true);
  useEffect(() => {
    const timer = setTimeout(() => {
      setRandomLoading(false);
      
    }, 1000);

    return () => {
      clearTimeout(timer);
    };
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
          
          <Footer />
        </>
      )}
    </>
  );
};

export default Like;
