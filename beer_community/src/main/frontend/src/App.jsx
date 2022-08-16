import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Menu from "./pages/Menu";
import Profile from "./pages/Profile";
import { GlobalStyle } from "./styles";
import Like from "./pages/Like";
import Random from "./pages/Random";
import Search from "./pages/Search";
import Rate from "./pages/Rate";
import Detail from "./pages/Detail";
import UserInfo from "./pages/UserInfo";
import Review from "./pages/Review";
import LoginRetry from "./pages/LoginRetry";
import { BrowserRouter } from "react-router-dom";
import HowToDrink from './pages/HowtoDrink';
import BeerType from './pages/BeerType';
function App() {
  return (
    <>
      <BrowserRouter>
        <GlobalStyle />
        <Routes>
          <Route path="/" element={<Login />} />
          <Route path="/login-retry" element={<LoginRetry />} />
          <Route path="/profile" element={<Profile />} />
          <Route path="/menu" element={<Menu />} />
          <Route path="/like" element={<Like />} />
          <Route path="/random" element={<Random />} />
          <Route path="/search" element={<Search />} />
          <Route path="/rate" element={<Rate />} />
          <Route path="/detail" element={<Detail />} />
          <Route path="/user-info" element={<UserInfo />} />
          <Route path="/review" element={<Review />} />
          <Route path="/howtodrink" element={<HowToDrink />} />
          <Route path="/beertype" element={<BeerType/>}/>
        </Routes>
      </BrowserRouter>
    </>
  );
}
export default App;
