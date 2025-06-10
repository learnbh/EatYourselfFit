import Tile from "../component/Tile.tsx";
import breakfast from "../assets/breakfast.jpg";
import cookAsia from "../assets/woman-cook-asia_640.jpg";
import chickendish from "../assets/chickendish.jpg";
import shoppingList from "../assets/shopping-list_640.jpg";
import { GiCook } from "react-icons/gi";
import { GrScheduleNew } from "react-icons/gr";
import { GrSchedule } from "react-icons/gr";
import { AiOutlineSchedule  } from "react-icons/ai";

export default function Home(){
    return(
        <>
            <div className="home">
                <Tile to="/recipe"
                      title="Rezepte"
                      element ={<GiCook className="m-1"/>}
                      img={chickendish}
                      imgAlt="picture shows a chicken dish from pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/weekplan"
                      title="Wochenplaner"
                      element = {<GrSchedule className="m-1"/>}
                      img={breakfast}
                      imgAlt="picture shows a Table with breakfast made by palacioerick, pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/recipeplan"
                      title="Rezepte erstellen"
                      element = {<GrScheduleNew className="m-1"/>}
                      img={cookAsia}
                      imgAlt="picture shows a woman cooking asian food from pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
                <Tile to="/shoppinglist"
                      title="Einkaufsliste"
                      element = {<AiOutlineSchedule className="m-1"/>}
                      img={shoppingList}
                      imgAlt="picture shows someone writing a shoppinglist, pixabay.com"
                      imgWidth="100%"
                      imgHeight="200"
                />
            </div>
        </>
    )
}