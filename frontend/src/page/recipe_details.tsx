import {type FormEvent, useCallback, useEffect, useRef, useState} from "react";
import axios from "axios";
import type {Ingredient, IngredientProductRef, RecipeDtoType, RecipeIngredientType, RecipeType} from "../types.ts";
import IngredientRecipe from "../component/IngredientRecipe.tsx";
import ShowError from "../component/ShowError.tsx";
import AddRecipe_layout from "../layout/addRecipe_layout.tsx";
import {handleAxiosFormError} from "../helper.ts";

export default function RecipeDetails(){

    const recipeId: string | null = sessionStorage.getItem("detailId");

    const [ingredients, setIngredients] = useState<Ingredient[]>([])
    const [recipeName, setRecipeName] = useState<string>("Rezeptname anpassen !");

    const refProduct = useRef<IngredientProductRef>({
        focusField: () => {}
    });

    const [isError, setError] = useState<string>("")

    const [isLoading, setLoading] = useState<boolean>(false)

    const getDetails = useCallback( async ( id: string | null ) => {
        let messages = { userMessage:"", logMessage:"" };
        try {
            if (id) {
                setLoading(true)
                const response = await axios.get("/eyf/recipe/" + id);
                const recipe: RecipeType = response.data;
                setRecipeName(recipe.title);
                const ingredients = await Promise.all(
                    recipe.recipeIngredients.map(async (ri) => {
                        const res = await axios.get("/eyf/ingredients/ingredient/detail/" + ri.ingredientId);
                        const ingredient: Ingredient = res.data;
                        ingredient.quantity = ri.quantity;
                        console.log(ingredient);
                        return ingredient;
                    })
                );

                setIngredients(ingredients);
            }
        } catch ( error ) {
            messages = handleAxiosFormError(
                error,
                refProduct,
                "product"
            );
            setError( messages.userMessage );
            console.error( messages.logMessage );
        } finally {
            setLoading(false)
        }
    }, [])

    useEffect( () => {
        (async () => {
            await getDetails(recipeId);
        })();
    }, [recipeId, getDetails] );

    function changeRecipeName(recipeName:string){
        if( recipeName === "" ) setRecipeName("Rezeptname anpassen !");
        else setRecipeName(recipeName);
    }

    function addToRecipe(ingredient:Ingredient){
        if(!ingredients.some((i:Ingredient) =>i.id === ingredient.id )){
            setIngredients(prevArr => [...prevArr, ingredient]);
        }
    }

    function removeFromRecipe(ingredient:Ingredient){
        setIngredients(prev => prev.filter(item => item.id !== ingredient.id));
    }

    function clearRecipe(): void {
        setIngredients([]);
        setRecipeName("Rezeptname anpassen !")
    }

    function changeQuantity(ingredient:Ingredient){
        setIngredients(prevArr => {
            const changedIngredients:Ingredient[] = prevArr.map(i =>
                i.id === ingredient.id
                    ? {...i, quantity: ingredient.quantity}
                    : i
            );
            return changedIngredients;
        });
    }

    const submit = async ( e:FormEvent<HTMLFormElement> )=> {
        e.preventDefault();
        let messages = { userMessage:"", logMessage:"" };
        if( recipeName !== "" && recipeName !== "Rezeptname anpassen !"){
            setError("");
            const recipeIngredients:RecipeIngredientType[] = ingredients.map((item:Ingredient)=> {
                return {
                    "ingredientId": item.id,
                    "quantity": item.quantity
                } as RecipeIngredientType
            });
            const recipeDto:RecipeDtoType = {
                title:recipeName,
                recipeIngredients: recipeIngredients
            }
            try {
                await axios.put("/eyf/recipe/"+recipeId, recipeDto,
                    {
                        headers: {
                            'Content-Type': 'application/json',
                        },
                    } );
                setError("Änderungen gespeichert!")
            } catch ( error ) {
                messages = handleAxiosFormError(
                    error,
                    refProduct,
                    "product"
                );
                setError( messages.userMessage );
                console.error( messages.logMessage );
            }
        }else {
            setError("Rezeptname fehlt.");
        }
    }
    return(
        <>
            {isLoading && (
                <h1> Die Details des Rezeptes werden geladen</h1>
            )}
            <div className="recipeplan">
                { isError !== "" && (
                    <ShowError message = { isError } />
                ) }
                <div>
                    <AddRecipe_layout
                        recipeName = {recipeName}
                        handleChangeRecipeName={changeRecipeName}
                        addIngredientToRecipe={addToRecipe}
                        removeIngredientFromRecipe={removeFromRecipe}
                        handleQuantity={changeQuantity}
                    />
                </div>
                <form  onSubmit={(e:FormEvent<HTMLFormElement>)=>submit(e)}>
                    <div className="flex flex-col border rounded shadow p-2 font-serif sm:min-w-sm">
                        <h2 className="text-2xl mb-2 p-2"><em>{recipeName}</em></h2>
                        <div className="">
                            {ingredients.length !== 0 ?
                                ingredients.map(i =>
                                    <IngredientRecipe
                                        key = {i.id}
                                        ingredient={i}
                                        handleQuantity={changeQuantity}
                                        removeIngredientFromRecipe={removeFromRecipe}
                                    />)
                                : <span className="text-xl"> Nutze die Zutatensuche, um Zutaten zum Rezept hinzuzufügen.</span>
                            }
                        </div>
                        <div className="grid grid-cols-1 sm:grid-cols-2 gap-2 mt-2 p-2">
                            <button className="border p-2 font-sans mt-auto" type="submit">
                                Rezept Speichern
                            </button>
                            <button className="border p-2 font-sans mt-auto" type="button" onClick={clearRecipe}>
                                Rezept leeren
                            </button>
                        </div>
                    </div>
                </form>
            </div>
        </>
    )
}