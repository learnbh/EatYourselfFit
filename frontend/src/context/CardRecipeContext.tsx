
import type {RecipeContextType, Ingredient} from "../types.ts";
import {createContext, type ReactNode, useContext, useState} from "react";


const RecipeContext = createContext<RecipeContextType | undefined>(undefined);

export function RecipeProvider({children}:{children: ReactNode}){
    const [recipeItems, setRecipeItems] = useState<Ingredient[]>([]);
    const [dishName, setDishname] = useState<string>("Rezeptname anpassen !");

    function changeDishName(dishName:string){
        if( dishName === "" ) setDishname("Rezeptname anpassen !");
        else setDishname(dishName);
    }

    function addToRecipe(ingredient:Ingredient){
        if(!recipeItems.some((i:Ingredient) =>i.id === ingredient.id )){
            setRecipeItems(prevArr => [...prevArr, ingredient]);
        }
    }

    function removeFromRecipe(ingredient:Ingredient){
        setRecipeItems(prev => prev.filter(item => item.id !== ingredient.id));
    }

    function clearRecipe(): void {
        setRecipeItems([]);
        setDishname("Rezeptname anpassen !")
    }

    function changeQuantity(ingredient:Ingredient){
        setRecipeItems(prevArr => {
            const changedIngredients:Ingredient[] = prevArr.map(i =>
                i.id === ingredient.id
                    ? {...i, quantity: ingredient.quantity}
                    : i
            );
            return changedIngredients;
        });
    }
    return (
        <RecipeContext.Provider value={{ dishName, recipeItems, addToRecipe, removeFromRecipe, clearRecipe, changeDishName, changeQuantity }}>
            {children}
        </RecipeContext.Provider>
    );
}
// 3. Custom Hook (zur besseren Wiederverwendung)
export function useRecipeCart(): RecipeContextType {
    const context = useContext(RecipeContext);
    if (!context) {
        throw new Error("useCart must be used within a CartProvider");
    }
    return context;
}