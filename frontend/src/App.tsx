import './App.css';
import './login.css'

import {Navigate, Route, Routes} from "react-router-dom";

import  { UserProvider } from './context/user/userProvider.tsx';

import HeaderImg from "./assets/nutritionist_header960x200.jpg";

import Header from "./component/Header.tsx";
import Footer from "./component/Footer.tsx";

import Home from "./page/home.tsx";
import Recipe from "./page/recipe.tsx";
import Recipeplan from "./page/recipeplan.tsx";
import Weekplan from "./page/weekplan.tsx";
import Shoppinglist from "./page/shoppinglist.tsx";
import IngredientDetails from "./page/ingredient_details.tsx";
import IngredientCreate from "./page/ingredient_create.tsx";
import RecipeDetails from "./page/recipe_details.tsx";
import Jobs from "./page/jobs.tsx";
import Login from "./page/login.tsx";
import Profile from './page/profile.tsx';
import LoginSuccess from './page/login_success.tsx';

function App() {  
  return (
    <>
    <UserProvider>
        <div className="app-container">
            <header>
                <img src={HeaderImg} alt="picture with food from freepick.com" width="100%" height="200"/>
                <Header/>
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
                    <Route path="/ingredient" element={<IngredientDetails/>}/>
                    <Route path="/ingredient/*" element={<Navigate to="/ingredient" />}/>
                    <Route path="/ingredient/add/:product" element={<IngredientCreate/>}/>
                    <Route path="/job/migrate/slugs" element={<Jobs/>}/>
                    <Route path="/login" element={<Login />}/>
                    <Route path="/profile" element={<Profile />} />
                    <Route path="/login/success" element={<LoginSuccess/>}/>
                </Routes>
            </main>
            <footer>
                <Footer />
            </footer>
        </div>
    </UserProvider>
    </>
  )
}

export default App
