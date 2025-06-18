import './App.css'
import Header from "./component/Header.tsx";
import Footer from "./component/Footer.tsx";
import HeaderImg from "./assets/nutritionist_header.jpg";
import Home from "./page/home.tsx";
import Recipe from "./page/recipe.tsx";
import {Route, Routes} from "react-router-dom";
import Recipeplan from "./page/recipeplan.tsx";
import Weekplan from "./page/weekplan.tsx";
import Shoppinglist from "./page/shoppinglist.tsx";
import IngredientDetails from "./page/ingredient_details.tsx";

function App() {

  return (
    <>
        <div className="app-container">
            <header>
                <img src={HeaderImg} alt="picture with food from freepick.com" width="100%" height="250"/>
                <Header />
            </header>
            <main className="main-content border">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/recipe" element={<Recipe/>}/>
                    <Route path="/recipeplan" element={<Recipeplan/>}/>
                    <Route path="/weekplan" element={<Weekplan/>}/>
                    <Route path="/shoppinglist" element={<Shoppinglist/>}/>
                    <Route path="/ingredient/" element={<IngredientDetails/>}/>
                </Routes>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    </>
  )
}

export default App
