import './App.css'
import Header from "./component/Header.tsx";
import Footer from "./component/Footer.tsx";
import HeaderImg from "./assets/nutritionist_header960x200.jpg";
import Home from "./page/home.tsx";
import Recipe from "./page/recipe.tsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Recipeplan from "./page/recipeplan.tsx";
import Weekplan from "./page/weekplan.tsx";
import Shoppinglist from "./page/shoppinglist.tsx";
import IngredientDetails from "./page/ingredient_details.tsx";
import IngredientCreate from "./page/ingredient_create.tsx";
import RecipeDetails from "./page/recipe_details.tsx";
import Jobs from "./page/jobs.tsx";

function App() {

  return (
    <>
        <div className="app-container">
            <header>
                <img src={HeaderImg} alt="picture with food from freepick.com" width="100%" height="200"/>
                <Header />
            </header>
            <main className="main-content border">
                <Routes>
                    <Route path="/" element={<Home/>}/>
                    <Route path="/recipe" element={<Recipe/>}/>
                    <Route path="/recipe/*" element={<Navigate to="/recipe/show"/>}/>
                    <Route path="/recipe/show" element={<RecipeDetails/>}/>
                    <Route path="/recipeplan" element={ <Recipeplan /> }/>
                    <Route path="/weekplan" element={<Weekplan/>}/>
                    <Route path="/shoppinglist" element={<Shoppinglist/>}/>
                    <Route path="/ingredient/" element={<IngredientDetails/>}/>
                    <Route path="/ingredient/*" element={<Navigate to="/ingredient" />}/>
                    <Route path="/ingredient/add/:product" element={<IngredientCreate/>}/>
                    <Route path="/job/migrate/slugs" element={<Jobs/>}/>
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
