import { Route, Routes } from "react-router-dom";
import Login from "./pages/Login";
import Menu from "./pages/Menu";
import Profile from "./pages/Profile";
import { GlobalStyle } from "./styles";

import Test from "./pages/Test";

function App() {
  return (
    <>
      <GlobalStyle />

      <Routes>
        <Route path="/" element={<Login />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/menu" element={<Menu />} />
        <Route path="/test" element={<Test />} />
      </Routes>
    </>
  );
}
export default App;
