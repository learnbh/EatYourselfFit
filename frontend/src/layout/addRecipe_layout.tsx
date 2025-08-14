import type {Ingredient} from "../types.ts";
import IngredientSearchResultRecipePlan from "../component/IngredientSearchResultRecipePlan.tsx";
import {type ChangeEvent, useCallback, useEffect, useState} from "react";
import axios from "axios";
import IngredientNotFound from "../component/IngredientNotFound.tsx";
import {useNavigate} from "react-router-dom";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";

type Props = {
    recipeName?: string
    handleChangeRecipeName: (recipeName:string) => void
    addIngredientToRecipe: (ingredient:Ingredient) => void
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
    handleQuantity:(ingredient:Ingredient) =>  void
}

export default function AddRecipe_layout(props:Readonly<Props>) {
    const { recipeItems } = useRecipeCart()
    const routeTo =useNavigate()

    const [ingredientSearch, setIngredientSearch] = useState<string>("");
    const [ingredientsSearch, setIngredientsSearch] = useState<Ingredient[]>([]);
    const [ingredientNotFoundVisible, setIngredientNotFoundVisible] = useState(false);
    const [isLoading, setIsLoading] = useState<boolean>(false);

    const getDBData = useCallback(async (search:string) => {
        if(search.trim() === ""){
            setIngredientsSearch([]);
            setIsLoading(false);
            setIngredientNotFoundVisible(false);
            return;
        }
        try {
            setIsLoading(true);
            const response = await axios.get("/eyf/ingredients/name/" + search);
            if (response.data.length > 0) {
                const searchResponse:Ingredient[] = response.data;
                const searchResponseNotInRecipe:Ingredient[] = searchResponse
                    .filter((i:Ingredient) => recipeItems.some((r:Ingredient) =>r.id === i.id )? null : i);
                setIngredientsSearch(searchResponseNotInRecipe);
                setIngredientNotFoundVisible(false);
            } else {
                setIngredientsSearch([]);
                setIngredientNotFoundVisible(true);
            }
        } catch (error){
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        }finally {
            setIsLoading(false);
        }
    }, [recipeItems]);

    const editIngredientGeneratedByOpenAi = useCallback(async (search:string) => {
        if(search.trim() === ""){
            setIngredientsSearch([]);
            setIsLoading(false);
            setIngredientNotFoundVisible(false);
            return;
        }
        try {
            setIsLoading(true);
            const response = await axios.get("/eyf/ingredients/name/" + search);
            if (response.data.length > 0) {
                const ingredientId:string = response.data[0].id;
                setIngredientsSearch(response.data);
                sessionStorage.setItem('detailId',ingredientId);
                window.open("/ingredient", "_blank");
                setIngredientNotFoundVisible(false);
            } else {
                setIngredientsSearch([]);
                setIngredientNotFoundVisible(true);
            }
        } catch (error){
            if (axios.isAxiosError(error)) {
                console.error('Axios error:', error.response?.data || error.message);
            } else {
                console.error('Unexpected error:', error);
            }
        }finally {
            setIsLoading(false);
        }
    }, []);

    function handleChangeRecipeName(e:ChangeEvent<HTMLInputElement>){
        e.preventDefault();
        props.handleChangeRecipeName(e.target.value);
    }
    function handleChangeIngredient(e:ChangeEvent<HTMLInputElement>){
        e.preventDefault();
        setIngredientSearch( e.target.value)
    }
    // for changing the quantity of an ingredient, that will be added to a recipe
    function handleQuantity(ingredient:Ingredient){
        props.handleQuantity(ingredient);
    }

    function addIngredientToRecipe(ingredient:Ingredient){
        setIngredientsSearch(prev => prev.filter(item => item.id !== ingredient.id));
        if (ingredientsSearch.length == 1){
            setIngredientSearch("");
        }
        props.addIngredientToRecipe(ingredient);
    }
    function removeIngredientFromRecipe(ingredient:Ingredient){
        props.removeIngredientFromRecipe(ingredient);
    }

    function abortAddIngredient (){
        setIngredientSearch("");
        setIngredientsSearch([]);
    }

    const addPerOpenAiIngredient = async () => {
        try {
            setIngredientNotFoundVisible(false);
            setIsLoading(true);
            await axios.post("/eyf/ingredients/openai/add", {product: ingredientSearch, variation:""})
            await editIngredientGeneratedByOpenAi(ingredientSearch);
        } catch (error){
            if (axios.isAxiosError(error)) {
                if (error.response && error.response.data.message) {
                    console.error('Axios errors_:', error.response?.data || error.message);
                    alert(error.response.data.message);
                } else {
                    console.log("error: "+error);
                    alert('Es konnten keine Nährstoffe gefunden werden. Änderne die Anfrage und versuche es erneut.');
                }
            } else {
                console.log(error);
            }
        }finally {
            setIsLoading(false);
        }
    }
    function addPerUser(){
        routeTo("/ingredient/add/"+ingredientSearch);
    }

    useEffect(() => {
        (async () => {
            await getDBData(ingredientSearch);
        })();
    }, [ingredientSearch, getDBData]);

    return(
        <>
            <form>
                <div className="flex flex-col">
                    <div className="flex flex-col">
                        <label>Name für das Rezept hinzufügen: </label>
                        <input
                            placeholder="Hier kann der Name für's Rezept rein"
                            value={props.recipeName}
                            name="dishname"
                            id="dishname"
                            onChange={handleChangeRecipeName}
                        />
                    </div>
                    <div className="flex flex-col">
                        <label>Zutaten hinzufügen:</label>
                        <input
                            className=" grow"
                            placeholder="Zutat suchen"
                            value={ingredientSearch}
                            name="addingredient"
                            id="addingredient"
                            onChange={handleChangeIngredient}
                        />
                    </div>
                    <div className="space-y-4">
                        {ingredientNotFoundVisible && (
                            <IngredientNotFound
                                addPerAi={addPerOpenAiIngredient}
                                addPerUser={addPerUser}
                                abort={abortAddIngredient}
                            />
                        )}
                        {ingredientSearch !== "" && ingredientsSearch.length > 0 && (
                            ingredientsSearch.map((i:Ingredient) =>
                                    <IngredientSearchResultRecipePlan
                                        key={i.id}
                                        ingredient={i}
                                        addIngredientToRecipe={addIngredientToRecipe}
                                        removeIngredientFromRecipe={removeIngredientFromRecipe}
                                        handleQuantity={handleQuantity}
                                    />
                            )
                        )}
                        {isLoading && (
                            <span>Daten werden geladen ...</span>
                        )}
                    </div>
                </div>
            </form>
        </>
    )
}