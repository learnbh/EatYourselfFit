import type {Ingredient} from "../types.ts";
import AddRecipe_layout from "../layout/addRecipe_layout.tsx";
import IngredientRecipe from "../component/IngredientRecipe.tsx";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";


export default function Recipeplan(){
    const { dishName, recipeItems, addToRecipe, removeFromRecipe, clearRecipe, changeDishName, changeQuantity } = useRecipeCart()

    function handleChangeDishName(dishName:string){
        changeDishName(dishName)
    }

    function handleAddToRecipe(ingredient:Ingredient){
        addToRecipe(ingredient);
    }

    function handleRemoveFromRecipe(ingredient:Ingredient){
        removeFromRecipe(ingredient);
    }
    function handleClearRecipe(){
        clearRecipe();
    }
    function handleChangeQuantity(ingredient:Ingredient){
        changeQuantity(ingredient)
    }

    function saveRecipe(){

    }

    return(
        <>
            <div>
                <h1>Hier kannst Du ein neues Gericht erstellen</h1>
                <div className="recipeplan">
                    <div>
                        <AddRecipe_layout
                            handleChangeDishName={handleChangeDishName}
                            addIngredientToRecipe={handleAddToRecipe}
                            removeIngredientFromRecipe={handleRemoveFromRecipe}
                            handleQuantity={handleChangeQuantity}
                        />
                    </div>
                    <div className="flex flex-col border rounded shadow p-2 font-serif h-full min-w-sm">
                        <h2 className="text-xl">{dishName}</h2>
                        <div className="flex-1 overflow-auto my-4">
                            { recipeItems.map(i =>
                                <IngredientRecipe
                                    key = {i.id}
                                    ingredient={i}
                                    handleQuantity={handleChangeQuantity}
                                    removeIngredientFromRecipe={handleRemoveFromRecipe}
                                />)
                            }
                        </div>
                        <div className="grid grid-cols-2">
                            <button className="border p-2 font-sans mt-auto" type="submit" onClick={saveRecipe}>
                                Rezept Speichern
                            </button>
                            <button className="border p-2 font-sans mt-auto" type="submit" onClick={handleClearRecipe}>
                                Rezept leeren
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        </>
    );
}