import type {
    Ingredient,
    IngredientProductRef,
    RecipeDtoType,
    RecipeIngredientType
} from "../types.ts";
import AddRecipe_layout from "../layout/addRecipe_layout.tsx";
import IngredientRecipe from "../component/IngredientRecipe.tsx";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import {type FormEvent, useRef, useState} from "react";
import axios from "axios";
import {handleAxiosFormError} from "../helper.ts";
import ShowError from "../component/ShowError.tsx";
import {useNavigate} from "react-router-dom";


export default function Recipeplan(){
    const { dishName, recipeItems, addToRecipe, removeFromRecipe, clearRecipe, changeDishName, changeQuantity } = useRecipeCart()
    const routeTo = useNavigate();
    const refProduct = useRef<IngredientProductRef>({
        focusField: () => {}
    });

    const [isError, setError] = useState<string>("")

    function handleChangeDishName(dishName:string){
        setError("");
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

    const submit = async ( e:FormEvent<HTMLFormElement> )=> {
        e.preventDefault();
        let messages = { userMessage:"", logMessage:"" };
        if( dishName !== "" && dishName !== "Rezeptname anpassen !"){
            setError("");
            const recipeIngredients:RecipeIngredientType[] = recipeItems.map((item:Ingredient)=> {
                return {
                    "ingredientId": item.id,
                    "quantity": item.quantity
                } as RecipeIngredientType
            });
            const recipeDto:RecipeDtoType = {
                title:dishName,
                recipeIngredients: recipeIngredients
            }
            try {
                await axios.post("/eyf/recipe", recipeDto,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    } );
            } catch ( error ) {
                messages = handleAxiosFormError(
                    error,
                    refProduct,
                    "product"
                );
                setError( messages.userMessage );
                console.error( messages.logMessage );
            }
            if( messages.userMessage === "" ) {
                handleClearRecipe();
                routeTo( "/recipe" );
            }
        }else {
            setError("Rezeptname fehlt.");
        }
    }

    return(
        <>
            <div>
                <h1>Hier kannst Du ein neues Gericht erstellen</h1>
                <div className="recipeplan">
                    <div>
                        { isError !== "" && (
                            <ShowError message = { isError } />
                        ) }
                        <AddRecipe_layout
                            handleChangeDishName={handleChangeDishName}
                            addIngredientToRecipe={handleAddToRecipe}
                            removeIngredientFromRecipe={handleRemoveFromRecipe}
                            handleQuantity={handleChangeQuantity}
                        />
                    </div>
                    <form  onSubmit={(e:FormEvent<HTMLFormElement>)=>submit(e)}>
                        <div className="flex flex-col border rounded shadow p-2 font-serif sm:min-w-sm">
                            <h2 className="text-2xl mb-2 p-2"><em>{dishName}</em></h2>
                            <div className="">
                                {recipeItems.length !== 0 ?
                                    recipeItems.map(i =>
                                        <IngredientRecipe
                                            key = {i.id}
                                            ingredient={i}
                                            handleQuantity={handleChangeQuantity}
                                            removeIngredientFromRecipe={handleRemoveFromRecipe}
                                        />)
                                    : <span className="text-xl"> Nutze die Zutatensuche, um Zutaten zum Rezept hinzuzufügen.</span>
                                }
                            </div>
                            <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 mt-2 p-2">
                                <button className="border p-2 font-sans mt-auto" type="submit">
                                    Rezept Speichern
                                </button>
                                <button className="border p-2 font-sans mt-auto" type="button" onClick={handleClearRecipe}>
                                    Rezept leeren
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </>
    );
}