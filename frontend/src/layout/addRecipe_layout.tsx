import type {Ingredient} from "../types.ts";
import IngredientSearchResultRecipePlan from "../component/IngredientSearchResultRecipePlan.tsx";
import axios from "axios";
import {useRecipeCart} from "../context/CardRecipeContext.tsx";
import IngredientSearch from "../component/IngredientSearch.tsx";
import IngredientNotFound from "../component/IngredientNotFound.tsx";
import {type ChangeEvent, useCallback, useEffect, useState} from "react";

type Props = {
    recipeName?: string
    handleChangeRecipeName: (recipeName:string) => void
    addIngredientToRecipe: (ingredient:Ingredient) => void
    removeIngredientFromRecipe: (ingredient:Ingredient) => void
    handleQuantity:(ingredient:Ingredient) =>  void
}

export default function AddRecipe_layout(props:Readonly<Props>) {
    const { recipeItems } = useRecipeCart()

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

    function handleChangeRecipeName(e:ChangeEvent<HTMLInputElement>){
        e.preventDefault();
        props.handleChangeRecipeName(e.target.value);
    }
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
                    <div className="flex flex-col mt-3.5">
                        <label>Zutaten hinzufügen:</label>
                        <IngredientSearch
                            placeholder="Zutat suchen"
                            name="addingredient"
                            searchWord={ingredientSearch}
                            setSearchWord={setIngredientSearch}
                            setSearchResult={setIngredientsSearch}
                            setSearchNotFoundVisible={setIngredientNotFoundVisible}
                            setIsLoading={setIsLoading}
                        />
                    </div>
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
                    <div className="space-y-4">
                        {ingredientNotFoundVisible && (
                            <IngredientNotFound
                                searchWord={ingredientSearch}
                                setSearchWord={setIngredientSearch}
                                setSearchResult={setIngredientsSearch}
                                setSearchNotFoundVisible={setIngredientNotFoundVisible}
                                setIsLoading={setIsLoading}
                            />
                        )}
                    </div>
                        {isLoading && (
                            <span>Daten werden geladen ...</span>
                        )}
                </div>
            </form>
        </>
    )
}