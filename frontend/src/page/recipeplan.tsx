import {useState} from "react";
import type {Ingredient} from "../types.ts";
import AddRecipe from "../component/AddRecipe.tsx";
import IngredientRecipe from "../component/IngredientRecipe.tsx";

export default function Recipeplan(){

    const [dishname, setDishname] = useState<string>("");
    const [ingredients, setIngredients] = useState<Ingredient[]>([]);


    function handleChangeDishName(dishName:string){
        setDishname(dishName)
    }
    console.log("dishname: "+dishname);

    function addIngredientToRecipe(ingredient:Ingredient){
        if(!ingredients.some((i:Ingredient) =>i.id === ingredient.id )){
            setIngredients(prevArr => [...prevArr, ingredient]);
        }
    }

    function removeIngredientFromRecipe(ingredient:Ingredient){
        if(ingredients.some((i:Ingredient) =>i.id === ingredient.id )){
            setIngredients(prevArr => {
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
        console.log(ingredients);
        setIngredients(prevArr => {
            const changedIngredients:Ingredient[] = prevArr.map(i =>
            i.id === ingredient.id
                ? {...i, quantity: ingredient.quantity}
                : i
            );
            return changedIngredients;
        });
        console.log(ingredients);
    }

    function saveRecipe(){

    }

    return(
        <>
            <div>
                <h1>Hier kannst Du ein neues Gericht erstellen</h1>
                <div className="recipeplan">
                    <div>
                        <AddRecipe
                            handleChangeDishName={handleChangeDishName}
                            addIngredientToRecipe={addIngredientToRecipe}
                            removeIngredientFromRecipe={removeIngredientFromRecipe}
                            handleQuantity={handleQuantity}
                        />
                    </div>
                    <div className="flex flex-col border rounded shadow p-2 font-serif h-full w-96">
                        <h2 className="text-xl">{dishname}</h2>
                        <div className="flex-1 overflow-auto my-4">
                            {ingredients.map(i =>
                                <IngredientRecipe
                                    key = {i.id}
                                    product = {i.product}
                                    variation = {i.variation}
                                    quantity = {i.quantity}
                                    unit = {i.unit}
                                />)
                            }
                        </div>
                        <button className="border p-2 font-sans mt-auto" type="submit" onClick={saveRecipe}>
                            Rezept Speichern
                        </button>
                    </div>
                </div>
            </div>
        </>
    );
}