import './App.css'
import Header from "./component/Header.tsx";
import Footer from "./component/Footer.tsx";
import HeaderImg from "./assets/nutritionist_header.jpg";
import Home from "./page/home.tsx";
import Recipe from "./page/recipe.tsx";
import {Navigate, Route, Routes} from "react-router-dom";
import Recipeplan from "./page/recipeplan.tsx";
import Weekplan from "./page/weekplan.tsx";
import Shoppinglist from "./page/shoppinglist.tsx";
import IngredientDetails from "./page/ingredient_details.tsx";
import IngredientCreate from "./page/ingredient_create.tsx";
import type {Ingredient} from "./types.ts";
import {useState} from "react";

function App() {
    const [recipeCart, setRecipeCart] = useState<Ingredient[]>([]);
    const [dishname, setDishname] = useState<string>("");


    function handleChangeDishName(dishName:string){
        setDishname(dishName)
    }

    function addIngredientToRecipe(ingredient:Ingredient){
        if(!recipeCart.some((i:Ingredient) =>i.id === ingredient.id )){
            setRecipeCart(prevArr => [...prevArr, ingredient]);
        }
    }

    function removeIngredientFromRecipe(ingredient:Ingredient){
        if(recipeCart.some((i:Ingredient) =>i.id === ingredient.id )){
            setRecipeCart(prevArr => {
                const changedIngredients:Ingredient[] = [];
                prevArr.map(i =>
                    i.id !== ingredient.id
                        ? changedIngredients.push(i)
                        : null
                );
                return changedIngredients;
            });
        }
    }
    function handleQuantity(ingredient:Ingredient){
        setRecipeCart(prevArr => {
            const changedIngredients:Ingredient[] = prevArr.map(i =>
                i.id === ingredient.id
                    ? {...i, quantity: ingredient.quantity}
                    : i
            );
            return changedIngredients;
        });
    }

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
                    <Route path="/recipeplan" element={
                        <Recipeplan
                            dishname = { dishname }
                            ingredients = { recipeCart }
                            handleChangeDishName = { handleChangeDishName }
                            addIngredientToRecipe = { addIngredientToRecipe }
                            removeIngredientFromRecipe = { removeIngredientFromRecipe }
                            handleQuantity  = { handleQuantity }
                        />
                    }/>
                    <Route path="/weekplan" element={<Weekplan/>}/>
                    <Route path="/shoppinglist" element={<Shoppinglist/>}/>
                    <Route path="/ingredient/" element={<IngredientDetails/>}/>
                    <Route path="/ingredient/*" element={<Navigate to="/ingredient" />}/>
                    <Route path="/ingredient/add/:product" element={<IngredientCreate/>}/>
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
